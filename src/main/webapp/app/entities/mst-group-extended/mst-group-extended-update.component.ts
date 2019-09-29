import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IMstGroup, MstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupExtendedService } from './mst-group-extended.service';
import { MstGroupUpdateComponent } from 'app/entities/mst-group';

@Component({
    selector: 'jhi-mst-group-extended-update',
    templateUrl: './mst-group-extended-update.component.html'
})
export class MstGroupExtendedUpdateComponent extends MstGroupUpdateComponent implements OnInit {
    groups: IMstGroup[];
    group: IMstGroup;

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
            if (this.mstGroup.mainGroup == undefined) this.group = new MstGroup();
        });

        this.mstGroupService
            .query({
                size: 200
            })
            .subscribe((response: HttpResponse<IMstGroup[]>) => {
                this.groups = [];
                this.groups = response.body;
            });

        if (this.mstGroup.mainGroup) {
            this.mstGroupService
                .find(this.mstGroup.mainGroup)
                .subscribe((response: HttpResponse<IMstGroup>) => (this.group = response.body));
        }
    }

    displayFn(mstGroup?: IMstGroup) {
        return mstGroup ? mstGroup.name : undefined;
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
