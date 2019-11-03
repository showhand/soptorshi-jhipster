import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';
import { CommercialPackagingDetailsService } from './commercial-packaging-details.service';
import { ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';
import { CommercialPackagingService } from 'app/entities/commercial-packaging';

@Component({
    selector: 'jhi-commercial-packaging-details-update',
    templateUrl: './commercial-packaging-details-update.component.html'
})
export class CommercialPackagingDetailsUpdateComponent implements OnInit {
    commercialPackagingDetails: ICommercialPackagingDetails;
    isSaving: boolean;

    commercialpackagings: ICommercialPackaging[];
    proDateDp: any;
    expDateDp: any;
    createOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPackagingDetailsService: CommercialPackagingDetailsService,
        protected commercialPackagingService: CommercialPackagingService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPackagingDetails }) => {
            this.commercialPackagingDetails = commercialPackagingDetails;
        });
        this.commercialPackagingService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPackaging[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPackaging[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPackaging[]) => (this.commercialpackagings = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commercialPackagingDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialPackagingDetailsService.update(this.commercialPackagingDetails));
        } else {
            this.subscribeToSaveResponse(this.commercialPackagingDetailsService.create(this.commercialPackagingDetails));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialPackagingDetails>>) {
        result.subscribe(
            (res: HttpResponse<ICommercialPackagingDetails>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCommercialPackagingById(index: number, item: ICommercialPackaging) {
        return item.id;
    }
}
