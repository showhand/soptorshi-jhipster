export interface IInventoryLocation {
    id?: number;
    name?: string;
    shortName?: string;
    description?: string;
}

export class InventoryLocation implements IInventoryLocation {
    constructor(public id?: number, public name?: string, public shortName?: string, public description?: string) {}
}
