import { Moment } from 'moment';

export interface IProductCategory {
    id?: number;
    name?: string;
    description?: any;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class ProductCategory implements IProductCategory {
    constructor(
        public id?: number,
        public name?: string,
        public description?: any,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {}
}
