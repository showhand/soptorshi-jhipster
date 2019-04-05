export interface IDepartment {
    id?: number;
    name?: string;
    shortName?: string;
}

export class Department implements IDepartment {
    constructor(public id?: number, public name?: string, public shortName?: string) {}
}
