import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';
import { CommercialAttachmentService } from 'app/entities/commercial-attachment';

type EntityResponseType = HttpResponse<ICommercialAttachment>;
type EntityArrayResponseType = HttpResponse<ICommercialAttachment[]>;

@Injectable({ providedIn: 'root' })
export class CommercialAttachmentExtendedService extends CommercialAttachmentService {
    public resourceUrl = SERVER_API_URL + 'api/extended/commercial-attachments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-attachments';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
