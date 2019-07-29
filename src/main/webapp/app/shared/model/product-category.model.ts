import { Moment } from 'moment';

export interface IProductCategory {
    id?: number;
    productCategoryId?: number;
    name?: string;
    description?: any;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class ProductCategory implements IProductCategory {
    constructor(
        public id?: number,
        public productCategoryId?: number,
        public name?: string,
        public description?: any,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {
        if (id) {
            productCategoryId = id;
        }
    }
}
