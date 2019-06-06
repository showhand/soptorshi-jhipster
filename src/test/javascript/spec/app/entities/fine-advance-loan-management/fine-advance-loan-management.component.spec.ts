/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { FineAdvanceLoanManagementComponent } from 'app/entities/fine-advance-loan-management/fine-advance-loan-management.component';
import { FineAdvanceLoanManagementService } from 'app/entities/fine-advance-loan-management/fine-advance-loan-management.service';
import { FineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';

describe('Component Tests', () => {
    describe('FineAdvanceLoanManagement Management Component', () => {
        let comp: FineAdvanceLoanManagementComponent;
        let fixture: ComponentFixture<FineAdvanceLoanManagementComponent>;
        let service: FineAdvanceLoanManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FineAdvanceLoanManagementComponent],
                providers: []
            })
                .overrideTemplate(FineAdvanceLoanManagementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FineAdvanceLoanManagementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FineAdvanceLoanManagementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FineAdvanceLoanManagement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.fineAdvanceLoanManagements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
