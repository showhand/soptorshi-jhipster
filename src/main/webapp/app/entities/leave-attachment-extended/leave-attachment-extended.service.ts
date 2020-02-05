import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ILeaveAttachment } from 'app/shared/model/leave-attachment.model';
import { LeaveAttachmentService } from 'app/entities/leave-attachment';

type EntityResponseType = HttpResponse<ILeaveAttachment>;
type EntityArrayResponseType = HttpResponse<ILeaveAttachment[]>;

@Injectable({ providedIn: 'root' })
export class LeaveAttachmentExtendedService extends LeaveAttachmentService {
    public resourceUrl = SERVER_API_URL + 'api/extended/leave-attachments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-attachments';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
