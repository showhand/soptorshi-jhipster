import { Moment } from 'moment';

export interface ICommercialPackagingDetails {
    id?: number;
    proDate?: Moment;
    expDate?: Moment;
    shift1?: string;
    shift1Total?: number;
    shift2?: string;
    shift2Total?: number;
    dayTotal?: number;
    total?: number;
    createdBy?: string;
    createOn?: Moment;
    updatedBy?: string;
    updatedOn?: string;
    commercialPackagingConsignmentNo?: string;
    commercialPackagingId?: number;
}

export class CommercialPackagingDetails implements ICommercialPackagingDetails {
    constructor(
        public id?: number,
        public proDate?: Moment,
        public expDate?: Moment,
        public shift1?: string,
        public shift1Total?: number,
        public shift2?: string,
        public shift2Total?: number,
        public dayTotal?: number,
        public total?: number,
        public createdBy?: string,
        public createOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: string,
        public commercialPackagingConsignmentNo?: string,
        public commercialPackagingId?: number
    ) {}
}
