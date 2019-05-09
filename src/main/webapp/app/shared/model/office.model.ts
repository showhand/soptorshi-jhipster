export interface IOffice {
    id?: number;
    name?: string;
    description?: string;
    location?: string;
}

export class Office implements IOffice {
    constructor(public id?: number, public name?: string, public description?: string, public location?: string) {}
}
