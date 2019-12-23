/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { MontlySalaryVouchersDeleteDialogComponent } from 'app/entities/montly-salary-vouchers/montly-salary-vouchers-delete-dialog.component';
import { MontlySalaryVouchersService } from 'app/entities/montly-salary-vouchers/montly-salary-vouchers.service';

describe('Component Tests', () => {
    describe('MontlySalaryVouchers Management Delete Component', () => {
        let comp: MontlySalaryVouchersDeleteDialogComponent;
        let fixture: ComponentFixture<MontlySalaryVouchersDeleteDialogComponent>;
        let service: MontlySalaryVouchersService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MontlySalaryVouchersDeleteDialogComponent]
            })
                .overrideTemplate(MontlySalaryVouchersDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MontlySalaryVouchersDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MontlySalaryVouchersService);
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
