export interface IPurchaseCommittee {
    id?: number;
    employeeFullName?: string;
    employeeId?: number;
}

export class PurchaseCommittee implements IPurchaseCommittee {
    constructor(public id?: number, public employeeFullName?: string, public employeeId?: number) {}
}
