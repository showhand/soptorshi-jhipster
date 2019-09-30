import { AfterContentInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { merge, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import { IMstGroup, ReservedFlag } from 'app/shared/model/mst-group.model';
import { MstGroupExtendedService } from './mst-group-extended.service';
import { MstGroupUpdateComponent } from 'app/entities/mst-group';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-mst-group-extended-update',
    templateUrl: './mst-group-extended-update.component.html'
})
export class MstGroupExtendedUpdateComponent extends MstGroupUpdateComponent implements OnInit {
    groups: IMstGroup[];
    groupNameList: string[];
    selectedGroupName: string;
    groupNameMapId: any;
    groupIdMapName: any;

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

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
            this.mstGroup.reservedFlag = this.mstGroup.reservedFlag === undefined ? ReservedFlag.NOT_RESERVED : this.mstGroup.reservedFlag;
        });

        this.mstGroupService
            .query({
                size: 2000
            })
            .subscribe((response: HttpResponse<IMstGroup[]>) => {
                this.groups = [];
                this.groups = response.body;
                this.groupNameMapId = {};
                this.groupIdMapName = {};
                this.groupNameList = [];
                this.groups.forEach((g: IMstGroup) => {
                    this.groupNameList.push(g.name);
                    this.groupNameMapId[g.name] = g;
                    this.groupIdMapName[g.id] = g.name;
                    if (g.id == this.mstGroup.mainGroup) this.selectedGroupName = g.name;
                });
            });
    }

    displayFn(mstGroup?: IMstGroup) {
        return mstGroup ? mstGroup.name : undefined;
    }

    save() {
        this.isSaving = true;
        this.mstGroup.mainGroup =
            this.groupNameMapId[this.selectedGroupName] != undefined ? this.groupNameMapId[this.selectedGroupName].id : undefined;
        if (this.mstGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.mstGroupExtendedService.update(this.mstGroup));
        } else {
            this.subscribeToSaveResponse(this.mstGroupExtendedService.create(this.mstGroup));
        }
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
}
