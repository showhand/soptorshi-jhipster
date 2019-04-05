export interface IDesignation {
    id?: number;
    name?: string;
    shortName?: string;
    description?: string;
}

export class Designation implements IDesignation {
    constructor(public id?: number, public name?: string, public shortName?: string, public description?: string) {}
}
