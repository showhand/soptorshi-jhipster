/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { FinancialAccountYearDeleteDialogComponent } from 'app/entities/financial-account-year/financial-account-year-delete-dialog.component';
import { FinancialAccountYearService } from 'app/entities/financial-account-year/financial-account-year.service';

describe('Component Tests', () => {
    describe('FinancialAccountYear Management Delete Component', () => {
        let comp: FinancialAccountYearDeleteDialogComponent;
        let fixture: ComponentFixture<FinancialAccountYearDeleteDialogComponent>;
        let service: FinancialAccountYearService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FinancialAccountYearDeleteDialogComponent]
            })
                .overrideTemplate(FinancialAccountYearDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FinancialAccountYearDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialAccountYearService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
