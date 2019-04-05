import { IAttachment } from 'app/shared/model/attachment.model';

export interface ITrainingInformation {
    id?: number;
    name?: string;
    subject?: string;
    organization?: string;
    attachments?: IAttachment[];
    employeeId?: number;
}

export class TrainingInformation implements ITrainingInformation {
    constructor(
        public id?: number,
        public name?: string,
        public subject?: string,
        public organization?: string,
        public attachments?: IAttachment[],
        public employeeId?: number
    ) {}
}
