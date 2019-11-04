export interface ICommercialAttachment {
    id?: number;
    fileContentType?: string;
    file?: any;
    commercialPurchaseOrderPurchaseOrderNo?: string;
    commercialPurchaseOrderId?: number;
    commercialPoStatusStatus?: string;
    commercialPoStatusId?: number;
}

export class CommercialAttachment implements ICommercialAttachment {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public commercialPurchaseOrderPurchaseOrderNo?: string,
        public commercialPurchaseOrderId?: number,
        public commercialPoStatusStatus?: string,
        public commercialPoStatusId?: number
    ) {}
}
