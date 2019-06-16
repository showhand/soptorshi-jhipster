export interface IAdvanceManagement {
    id?: number;
}

export class AdvanceManagement implements IAdvanceManagement {
    constructor(public id?: number) {}
}
