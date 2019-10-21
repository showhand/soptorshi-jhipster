import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { merge, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapExtendedService } from './system-group-map-extended.service';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from 'app/entities/mst-group';
import { SystemGroupMapUpdateComponent } from 'app/entities/system-group-map';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-system-group-map-update',
    templateUrl: './system-group-map-extended-update.component.html'
})
export class SystemGroupMapExtendedUpdateComponent extends SystemGroupMapUpdateComponent implements OnInit {
    selectedGroupName: string;
    groupNameMapId: any;
    groupIdMapName: any;
    groupNameList: string[];

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected systemGroupMapService: SystemGroupMapExtendedService,
        protected mstGroupService: MstGroupService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, systemGroupMapService, mstGroupService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ systemGroupMap }) => {
            this.systemGroupMap = systemGroupMap;
        });
        this.mstGroupService
            .query({
                size: 2000
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IMstGroup[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstGroup[]>) => response.body)
            )
            .subscribe(
                (res: IMstGroup[]) => {
                    this.mstgroups = res;
                    this.groupIdMapName = {};
                    this.groupNameMapId = {};
                    this.groupNameList = [];
                    res.forEach((g: IMstGroup) => {
                        this.groupNameMapId[g.name] = g.id;
                        this.groupIdMapName[g.id] = g.name;
                        this.groupNameList.push(g.name);
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search = (text$: Observable<string>) => {
        const debouncedText$ = text$.pipe(
            debounceTime(200),
            distinctUntilChanged()
        );
        const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
        const inputFocus$ = this.focus$;

        return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
            map(term =>
                (term === '' ? this.groupNameList : this.groupNameList.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(
                    0,
                    10
                )
            )
        );
    };

    save() {
        if (this.selectedGroupName == undefined) {
            this.jhiAlertService.error('Group is not selected');
        } else {
            this.isSaving = true;
            this.systemGroupMap.groupId = this.groupNameMapId[this.selectedGroupName];
            if (this.systemGroupMap.id !== undefined) {
                this.subscribeToSaveResponse(this.systemGroupMapService.update(this.systemGroupMap));
            } else {
                this.subscribeToSaveResponse(this.systemGroupMapService.create(this.systemGroupMap));
            }
        }
    }
}
