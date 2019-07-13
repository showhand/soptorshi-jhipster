export interface IBudgetAllocation {
    id?: number;
    amount?: number;
    officeName?: string;
    officeId?: number;
    departmentName?: string;
    departmentId?: number;
    financialAccountYearId?: number;
}

export class BudgetAllocation implements IBudgetAllocation {
    constructor(
        public id?: number,
        public amount?: number,
        public officeName?: string,
        public officeId?: number,
        public departmentName?: string,
        public departmentId?: number,
        public financialAccountYearId?: number
    ) {}
}
