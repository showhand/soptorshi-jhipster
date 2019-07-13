import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { TermsAndConditionsService } from './terms-and-conditions.service';
import { TermsAndConditionsComponent } from './terms-and-conditions.component';
import { TermsAndConditionsDetailComponent } from './terms-and-conditions-detail.component';
import { TermsAndConditionsUpdateComponent } from './terms-and-conditions-update.component';
import { TermsAndConditionsDeletePopupComponent } from './terms-and-conditions-delete-dialog.component';
import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';

@Injectable({ providedIn: 'root' })
export class TermsAndConditionsResolve implements Resolve<ITermsAndConditions> {
    constructor(private service: TermsAndConditionsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITermsAndConditions> {
        const id = route.params['id'] ? route.params['id'] : null;
        const purchaseOrderId = route.params['purchaseOrderId'] ? route.params['purchaseOrderId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TermsAndConditions>) => response.ok),
                map((termsAndConditions: HttpResponse<TermsAndConditions>) => termsAndConditions.body)
            );
        } else if (purchaseOrderId) {
            const termsAndConditions = new TermsAndConditions();
            termsAndConditions.purchaseOrderId = purchaseOrderId;
            return of(termsAndConditions);
        }
        return of(new TermsAndConditions());
    }
}

export const termsAndConditionsRoute: Routes = [
    {
        path: '',
        component: TermsAndConditionsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':purchaseOrderId/purchaseOrder',
        component: TermsAndConditionsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            termsAndConditions: TermsAndConditionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TermsAndConditionsDetailComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':purchaseOrderId/new',
        component: TermsAndConditionsUpdateComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TermsAndConditionsUpdateComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TermsAndConditionsUpdateComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const termsAndConditionsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TermsAndConditionsDeletePopupComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
