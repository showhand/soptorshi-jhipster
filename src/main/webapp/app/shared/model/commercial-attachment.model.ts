export interface ICommercialAttachment {
    id?: number;
    fileContentType?: string;
    file?: any;
    commercialPiProformaNo?: string;
    commercialPiId?: number;
}

export class CommercialAttachment implements ICommercialAttachment {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public commercialPiProformaNo?: string,
        public commercialPiId?: number
    ) {}
}
