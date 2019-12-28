import { Moment } from 'moment';

export interface IPurchaseOrderMessages {
    id?: number;
    comments?: any;
    commentedOn?: Moment;
    commenterFullName?: string;
    commenterId?: number;
    purchaseOrderId?: number;
}

export class PurchaseOrderMessages implements IPurchaseOrderMessages {
    constructor(
        public id?: number,
        public comments?: any,
        public commentedOn?: Moment,
        public commenterFullName?: string,
        public commenterId?: number,
        public purchaseOrderId?: number
    ) {}
}
