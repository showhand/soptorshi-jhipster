export interface IAttachment {
    id?: number;
    fileName?: string;
    path?: string;
    academicInformationId?: number;
    trainingInformationId?: number;
    experienceInformationId?: number;
}

export class Attachment implements IAttachment {
    constructor(
        public id?: number,
        public fileName?: string,
        public path?: string,
        public academicInformationId?: number,
        public trainingInformationId?: number,
        public experienceInformationId?: number
    ) {}
}
