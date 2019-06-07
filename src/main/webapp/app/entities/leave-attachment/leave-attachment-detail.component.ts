import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILeaveAttachment } from 'app/shared/model/leave-attachment.model';

@Component({
    selector: 'jhi-leave-attachment-detail',
    templateUrl: './leave-attachment-detail.component.html'
})
export class LeaveAttachmentDetailComponent implements OnInit {
    leaveAttachment: ILeaveAttachment;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveAttachment }) => {
            this.leaveAttachment = leaveAttachment;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
