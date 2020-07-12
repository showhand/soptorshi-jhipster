export interface IScmAreaWiseSelectedProduct {
    productCategoryId: number;
    productCategoryName: string;
    productId: number;
    productName: string;
    quantity: number;
    price: number;
}

export class ScmAreaWiseSelectedProduct implements IScmAreaWiseSelectedProduct {
    constructor(
        public productCategoryId: number,
        public productCategoryName: string,
        public productId: number,
        public productName: string,
        public quantity: number,
        public price: number
    ) {}
}
