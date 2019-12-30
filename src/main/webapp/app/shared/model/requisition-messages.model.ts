import { Moment } from 'moment';

export interface IRequisitionMessages {
    id?: number;
    comments?: any;
    commentedOn?: Moment;
    commenterFullName?: string;
    commenterId?: number;
    requisitionId?: number;
}

export class RequisitionMessages implements IRequisitionMessages {
    constructor(
        public id?: number,
        public comments?: any,
        public commentedOn?: Moment,
        public commenterFullName?: string,
        public commenterId?: number,
        public requisitionId?: number
    ) {}
}
