export const enum InventorySubLocationCategory {
    SHELF = 'SHELF',
    FREEZER = 'FREEZER',
    OTHERS = 'OTHERS'
}

export interface IInventorySubLocation {
    id?: number;
    category?: InventorySubLocationCategory;
    name?: string;
    shortName?: string;
    description?: string;
    inventoryLocationName?: string;
    inventoryLocationId?: number;
}

export class InventorySubLocation implements IInventorySubLocation {
    constructor(
        public id?: number,
        public category?: InventorySubLocationCategory,
        public name?: string,
        public shortName?: string,
        public description?: string,
        public inventoryLocationName?: string,
        public inventoryLocationId?: number
    ) {}
}
