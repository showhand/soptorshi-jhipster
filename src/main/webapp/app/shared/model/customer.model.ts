import { Moment } from 'moment';

export interface ICustomer {
    id?: number;
    name?: string;
    description?: any;
    address?: any;
    contactNo?: string;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public name?: string,
        public description?: any,
        public address?: any,
        public contactNo?: string,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {}
}
