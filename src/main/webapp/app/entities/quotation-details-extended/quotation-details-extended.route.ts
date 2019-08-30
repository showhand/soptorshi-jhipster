import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { QuotationDetails } from 'app/shared/model/quotation-details.model';
import { IQuotationDetails } from 'app/shared/model/quotation-details.model';
import { QuotationDetailsDeletePopupComponent, QuotationDetailsService } from 'app/entities/quotation-details';
import { QuotationDetailsExtendedComponent } from 'app/entities/quotation-details-extended/quotation-details-extended.component';
import { QuotationDetailsExtendedUpdateComponent } from 'app/entities/quotation-details-extended/quotation-details-extended-update.component';
import { QuotationDetailsExtendedDetailComponent } from 'app/entities/quotation-details-extended/quotation-details-extended-detail.component';
import { IQuotation, Quotation } from 'app/shared/model/quotation.model';

@Injectable({ providedIn: 'root' })
export class QuotationDetailsExtendedResolve {
    constructor(private service: QuotationDetailsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuotationDetails> {
        const id = route.params['id'] ? route.params['id'] : null;
        const quotationId = route.params['quotationId'] ? route.params['quotationId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<QuotationDetails>) => response.ok),
                map((quotationDetails: HttpResponse<QuotationDetails>) => quotationDetails.body)
            );
        } else if (quotationId) {
            const quotationDetails = new QuotationDetails();
            quotationDetails.quotationId = quotationId;
            return of(quotationDetails);
        }
        return of(new QuotationDetails());
    }
}

export const quotationDetailsExtendedRoute: Routes = [
    {
        path: '',
        component: QuotationDetailsExtendedComponent,
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
        component: QuotationDetailsExtendedDetailComponent,
        resolve: {
            quotationDetails: QuotationDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: QuotationDetailsExtendedUpdateComponent,
        resolve: {
            quotationDetails: QuotationDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':quotationId/new',
        component: QuotationDetailsExtendedUpdateComponent,
        resolve: {
            quotationDetails: QuotationDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: QuotationDetailsExtendedUpdateComponent,
        resolve: {
            quotationDetails: QuotationDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotationDetailsExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: QuotationDetailsDeletePopupComponent,
        resolve: {
            quotationDetails: QuotationDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
