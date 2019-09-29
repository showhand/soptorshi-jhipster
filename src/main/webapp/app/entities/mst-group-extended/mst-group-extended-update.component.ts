import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupExtendedService } from './mst-group-extended.service';
import { MstGroupUpdateComponent } from 'app/entities/mst-group';

@Component({
    selector: 'jhi-mst-group-extended-update',
    templateUrl: './mst-group-extended-update.component.html'
})
export class MstGroupExtendedUpdateComponent extends MstGroupUpdateComponent implements OnInit {
    groups: IMstGroup[];

    constructor(
        protected mstGroupService: MstGroupExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected mstGroupExtendedService: MstGroupExtendedService
    ) {
        super(mstGroupService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mstGroup }) => {
            this.mstGroup = mstGroup;
        });

        this.mstGroupService
            .query({
                size: 200
            })
            .subscribe((response: HttpResponse<IMstGroup[]>) => {
                this.groups = [];
                this.groups = response.body;
            });
    }

    save() {
        this.isSaving = true;
        if (this.mstGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.mstGroupExtendedService.update(this.mstGroup));
        } else {
            this.subscribeToSaveResponse(this.mstGroupExtendedService.create(this.mstGroup));
        }
    }
}
