import http from 'k6/http';

export const options = {

    scenarios: {

        buyers: {
            executor: 'shared-iterations',
            vus: 300,
            iterations: 1000,
            maxDuration: '30s'
        }
    }
};

export default function () {

    http.post(
        'http://localhost:8080/api/products/100/buy'
    );
}