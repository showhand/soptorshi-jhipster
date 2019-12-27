export interface ICommercialAttachment {
    id?: number;
    fileContentType?: string;
    file?: any;
    commercialPoPurchaseOrderNo?: string;
    commercialPoId?: number;
}

export class CommercialAttachment implements ICommercialAttachment {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public commercialPoPurchaseOrderNo?: string,
        public commercialPoId?: number
    ) {}
}
