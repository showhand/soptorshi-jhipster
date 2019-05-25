export interface ITax {
    id?: number;
    rate?: number;
    financialAccountYearId?: number;
}

export class Tax implements ITax {
    constructor(public id?: number, public rate?: number, public financialAccountYearId?: number) {}
}
