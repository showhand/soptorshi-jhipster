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
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
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
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialPackagingConsignmentNo?: string,
        public commercialPackagingId?: number
    ) {}
}
