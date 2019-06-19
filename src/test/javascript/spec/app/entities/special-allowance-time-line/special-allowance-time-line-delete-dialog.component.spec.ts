/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SpecialAllowanceTimeLineDeleteDialogComponent } from 'app/entities/special-allowance-time-line/special-allowance-time-line-delete-dialog.component';
import { SpecialAllowanceTimeLineService } from 'app/entities/special-allowance-time-line/special-allowance-time-line.service';

describe('Component Tests', () => {
    describe('SpecialAllowanceTimeLine Management Delete Component', () => {
        let comp: SpecialAllowanceTimeLineDeleteDialogComponent;
        let fixture: ComponentFixture<SpecialAllowanceTimeLineDeleteDialogComponent>;
        let service: SpecialAllowanceTimeLineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SpecialAllowanceTimeLineDeleteDialogComponent]
            })
                .overrideTemplate(SpecialAllowanceTimeLineDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SpecialAllowanceTimeLineDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpecialAllowanceTimeLineService);
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
