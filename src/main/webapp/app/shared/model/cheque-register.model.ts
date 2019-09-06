import { Moment } from 'moment';

export interface IChequeRegister {
    id?: number;
    chequeNo?: string;
    chequeDate?: Moment;
    status?: string;
    realizationDate?: Moment;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class ChequeRegister implements IChequeRegister {
    constructor(
        public id?: number,
        public chequeNo?: string,
        public chequeDate?: Moment,
        public status?: string,
        public realizationDate?: Moment,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {}
}
