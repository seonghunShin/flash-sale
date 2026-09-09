import http from 'k6/http';
import exec from 'k6/execution';
import { check } from 'k6';
import { Counter } from 'k6/metrics';

const reserveSuccess =
    new Counter('reserve_success');

const reserveFail =
    new Counter('reserve_fail');

const requestError =
    new Counter('reserve_request_error');

const BASE_URL =
    'http://127.0.0.1:8080';

export const options = {

    setupTimeout: '2m',

    scenarios: {

        reserve: {

            executor: 'shared-iterations',

            // 이번 실험의 목적은
            // 서버 최대 부하가 아니라 재고 정합성 확인
            vus: 100,

            iterations: 1000,

            maxDuration: '1m'
        }
    }
};


// --------------------------------------------------
// 테스트 시작 전 한 번만 실행
// --------------------------------------------------

export function setup() {

    // 1. user 1 ~ 1000 대기열 등록

    for (let userId = 1; userId <= 1000; userId++) {

        const res =
            http.post(
                `${BASE_URL}/api/events/1/queue?userId=${userId}`
            );

        if (res.status !== 200) {

            throw new Error(
                `Queue 등록 실패 userId=${userId}`
            );
        }
    }


    // 2. 1000명 전부 입장

    const admitResponse =
        http.post(
            `${BASE_URL}/api/events/1/admit?count=1000`
        );

    if (admitResponse.status !== 200) {

        throw new Error(
            'Admit 실패'
        );
    }


    // 3. 재고 500개 초기화

    const stockResponse =
        http.post(
            `${BASE_URL}/api/events/1/stock/init?stock=500`
        );

    if (stockResponse.status !== 200) {

        throw new Error(
            'Stock 초기화 실패'
        );
    }

    console.log(
        '테스트 준비 완료'
    );
}


// --------------------------------------------------
// 실제 동시성 테스트
// --------------------------------------------------

export default function () {

    // 전체 테스트 기준으로 0 ~ 999
    // 따라서 userId는 정확히 1 ~ 1000
    const userId =
        exec.scenario.iterationInTest + 1;


    const res =
        http.post(
            `${BASE_URL}/api/events/1/reserve?userId=${userId}`
        );


    // TCP 오류 / Connection Refused / HTTP 오류
    if (res.status !== 200) {

        requestError.add(1);

        return;
    }


    // 실제 재고 Hold 성공
    if (res.body === 'true') {

        reserveSuccess.add(1);

        return;
    }


    // 재고 부족 등의 정상적인 비즈니스 실패
    if (res.body === 'false') {

        reserveFail.add(1);
    }


    check(res, {

        'response is boolean':
            (r) =>
                r.body === 'true' ||
                r.body === 'false'
    });
}