export interface IProvidentManagement {
    id?: number;
}

export class ProvidentManagement implements IProvidentManagement {
    constructor(public id?: number) {}
}
