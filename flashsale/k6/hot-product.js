import http from 'k6/http';

export const options = {

    scenarios: {

        hot_product: {
            executor: 'constant-arrival-rate',
            rate: 500,
            timeUnit: '1s',
            duration: '30s',
            preAllocatedVUs: 100,
            maxVUs: 500
        }
    }
};

export default function () {

    http.get(
        'http://localhost:8080/api/products/100'
    );
}