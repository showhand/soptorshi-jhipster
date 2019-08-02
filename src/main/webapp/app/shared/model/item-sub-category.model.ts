export interface IItemSubCategory {
    id?: number;
    name?: string;
    shortName?: string;
    description?: string;
    itemCategoriesName?: string;
    itemCategoriesId?: number;
}

export class ItemSubCategory implements IItemSubCategory {
    constructor(
        public id?: number,
        public name?: string,
        public shortName?: string,
        public description?: string,
        public itemCategoriesName?: string,
        public itemCategoriesId?: number
    ) {}
}
