export interface IAllowanceManagement {
    id?: number;
}

export class AllowanceManagement implements IAllowanceManagement {
    constructor(public id?: number) {}
}
