export interface IMontlySalaryVouchers {
    id?: number;
}

export class MontlySalaryVouchers implements IMontlySalaryVouchers {
    constructor(public id?: number) {}
}
