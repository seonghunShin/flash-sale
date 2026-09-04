import http from 'k6/http';
import { check } from 'k6';

export const options = {

    scenarios: {

        name_search: {
            executor: 'constant-arrival-rate',
            exec: 'nameSearch',
            rate: 50,
            timeUnit: '1s',
            duration: '30s',
            preAllocatedVUs: 30,
            maxVUs: 150
        },

        brand_search: {
            executor: 'constant-arrival-rate',
            exec: 'brandSearch',
            rate: 50,
            timeUnit: '1s',
            duration: '30s',
            preAllocatedVUs: 30,
            maxVUs: 150
        },

        user_orders: {
            executor: 'constant-arrival-rate',
            exec: 'userOrders',
            rate: 50,
            timeUnit: '1s',
            duration: '30s',
            preAllocatedVUs: 30,
            maxVUs: 150
        }
    }
};

export function nameSearch() {

    const res =
        http.get(
            'http://localhost:8080/api/products/search?name=Product%20150000'
        );

    check(res, {
        'name search 200':
            (r) => r.status === 200
    });
}

export function brandSearch() {

    const res =
        http.get(
            'http://localhost:8080/api/products?brand=NIKE'
        );

    check(res, {
        'brand search 200':
            (r) => r.status === 200
    });
}

export function userOrders() {

    const res =
        http.get(
            'http://localhost:8080/api/users/42/orders'
        );

    check(res, {
        'orders 200':
            (r) => r.status === 200
    });
}