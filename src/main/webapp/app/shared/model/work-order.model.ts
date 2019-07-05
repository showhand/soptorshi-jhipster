import { Moment } from 'moment';

export interface IWorkOrder {
    id?: number;
    referenceNo?: string;
    issueDate?: Moment;
    referredTo?: string;
    subject?: string;
    note?: any;
    laborOrOtherAmount?: number;
    discount?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    requisitionRequisitionNo?: string;
    requisitionId?: number;
}

export class WorkOrder implements IWorkOrder {
    constructor(
        public id?: number,
        public referenceNo?: string,
        public issueDate?: Moment,
        public referredTo?: string,
        public subject?: string,
        public note?: any,
        public laborOrOtherAmount?: number,
        public discount?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public requisitionRequisitionNo?: string,
        public requisitionId?: number
    ) {}
}
