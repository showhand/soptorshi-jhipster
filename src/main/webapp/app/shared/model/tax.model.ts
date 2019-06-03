export const enum TaxStatus {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE'
}

export interface ITax {
    id?: number;
    minimumSalary?: number;
    rate?: number;
    taxStatus?: TaxStatus;
    financialAccountYearId?: number;
}

export class Tax implements ITax {
    constructor(
        public id?: number,
        public minimumSalary?: number,
        public rate?: number,
        public taxStatus?: TaxStatus,
        public financialAccountYearId?: number
    ) {}
}
