/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { MonthlySalaryDeleteDialogComponent } from 'app/entities/monthly-salary/monthly-salary-delete-dialog.component';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';

describe('Component Tests', () => {
    describe('MonthlySalary Management Delete Component', () => {
        let comp: MonthlySalaryDeleteDialogComponent;
        let fixture: ComponentFixture<MonthlySalaryDeleteDialogComponent>;
        let service: MonthlySalaryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MonthlySalaryDeleteDialogComponent]
            })
                .overrideTemplate(MonthlySalaryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MonthlySalaryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MonthlySalaryService);
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
