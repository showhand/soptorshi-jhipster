export interface IFineAdvanceLoanManagement {
    id?: number;
}

export class FineAdvanceLoanManagement implements IFineAdvanceLoanManagement {
    constructor(public id?: number) {}
}
