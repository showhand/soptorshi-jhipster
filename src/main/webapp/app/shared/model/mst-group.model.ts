import { Moment } from 'moment';

export const enum ReservedFlag {
    RESERVED = 'RESERVED',
    NOT_RESERVED = 'NOT_RESERVED'
}

export interface IMstGroup {
    id?: number;
    name?: string;
    mainGroup?: number;
    reservedFlag?: ReservedFlag;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class MstGroup implements IMstGroup {
    constructor(
        public id?: number,
        public name?: string,
        public mainGroup?: number,
        public reservedFlag?: ReservedFlag,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {}
}
