export interface ILoanManagement {
    id?: number;
}

export class LoanManagement implements ILoanManagement {
    constructor(public id?: number) {}
}
