export interface IAttachment {
    id?: number;
    fileContentType?: string;
    file?: any;
    academicInformationId?: number;
    trainingInformationId?: number;
    experienceInformationId?: number;
}

export class Attachment implements IAttachment {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public academicInformationId?: number,
        public trainingInformationId?: number,
        public experienceInformationId?: number
    ) {}
}
