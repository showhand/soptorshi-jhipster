export interface IItemCategory {
    id?: number;
    name?: string;
    shortName?: string;
    description?: string;
}

export class ItemCategory implements IItemCategory {
    constructor(public id?: number, public name?: string, public shortName?: string, public description?: string) {}
}
