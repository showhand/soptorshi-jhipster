import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { QuotationDetails } from 'app/shared/model/quotation-details.model';
import { QuotationDetailsService } from './quotation-details.service';
import { QuotationDetailsComponent } from './quotation-details.component';
import { QuotationDetailsDetailComponent } from './quotation-details-detail.component';
import { QuotationDetailsUpdateComponent } from './quotation-details-update.component';
import { QuotationDetailsDeletePopupComponent } from './quotation-details-delete-dialog.component';
import { IQuotationDetails } from 'app/shared/model/quotation-details.model';

@Injectable({ providedIn: 'root' })
export class QuotationDetailsResolve implements Resolve<IQuotationDetails> {
    constructor(private service: QuotationDetailsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuotationDetails> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<QuotationDetails>) => response.ok),
                map((quotationDetails: HttpResponse<QuotationDetails>) => quotationDetails.body)
            );
        }
        return of(new QuotationDetails());
    }
}

export const quotationDetailsRoute: Routes = [
    {
        path: '',
        component: QuotationDetailsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: QuotationDetailsDetailComponent,
        resolve: {
            quotationDetails: QuotationDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: QuotationDetailsUpdateComponent,
        resolve: {
            quotationDetails: QuotationDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: QuotationDetailsUpdateComponent,
        resolve: {
            quotationDetails: QuotationDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotationDetailsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: QuotationDetailsDeletePopupComponent,
        resolve: {
            quotationDetails: QuotationDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
