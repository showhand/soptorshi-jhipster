export interface IFineManagement {
    id?: number;
}

export class FineManagement implements IFineManagement {
    constructor(public id?: number) {}
}
