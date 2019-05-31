export interface IPayrollManagement {
    id?: number;
}

export class PayrollManagement implements IPayrollManagement {
    constructor(public id?: number) {}
}
